import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Alex Nevsky
 *
 * @link https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php
 *
 * Date: 12/08/2021
 */
public class BaseballElimination {

  private final Map<String, Integer> teams;
  private final int[] wins;
  private final int[] loss;
  private final int[] remaining;
  private final int[][] games;
  private final Map<String, Iterable<String>> eliminationMap;

  // create a baseball division from given filename in format specified below
  public BaseballElimination(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("arg is null");
    }

    In in = new In(filename);
    int n = in.readInt();

    teams = new HashMap<>(n);
    wins = new int[n];
    loss = new int[n];
    remaining = new int[n];
    games = new int[n][n];
    eliminationMap = new HashMap<>(n);

    int teamIndex = 0;
    while (!in.isEmpty()) {
      final String name = in.readString();
      teams.put(name, teamIndex);
      eliminationMap.put(name, Collections.emptyList());
      wins[teamIndex] = in.readInt();
      loss[teamIndex] = in.readInt();
      remaining[teamIndex] = in.readInt();
      for (int numGames = 0; numGames < games[teamIndex].length; ++numGames) {
        games[teamIndex][numGames] = in.readInt();
      }
      ++teamIndex;
    }
    in.close();
  }

  // number of teams
  public int numberOfTeams() {
    return teams.size();
  }

  // all teams
  public Iterable<String> teams() {
    return teams.keySet();
  }

  // number of wins for given team
  public int wins(String team) {
    validateTeam(team);
    return wins[teams.get(team)];
  }

  // number of losses for given team
  public int losses(String team) {
    validateTeam(team);
    return loss[teams.get(team)];
  }

  // number of remaining games for given team
  public int remaining(String team) {
    validateTeam(team);
    return remaining[teams.get(team)];
  }

  // number of remaining games between team1 and team2
  public int against(String team1, String team2) {
    validateTeam(team1);
    validateTeam(team2);
    return games[teams.get(team1)][teams.get(team2)];
  }

  // is given team eliminated?
  public boolean isEliminated(String team) {
    validateTeam(team);
    if (isTrivialEliminated(team)) return true;
    compute(team);
    return eliminationMap.get(team) != null;
  }

  // subset R of teams that eliminates given team; null if not eliminated
  public Iterable<String> certificateOfElimination(String team) {
    validateTeam(team);
    if (isTrivialEliminated(team)) buildTrivialEliminationCertificate(team);
    compute(team);
    return eliminationMap.get(team);
  }

  /**
   * Create a flow network and solve a maxflow problem in it
   *
   * @param team name
   */
  private void compute(String team) {
    // check if already computed
    if (eliminationMap.get(team) == null || eliminationMap.get(team).iterator().hasNext()) return;
    eliminationMap.put(team, null);
    FlowNetwork flowNetwork = createFlowNetwork(team);
    int s = 0;
    int t = flowNetwork.V() - 1;
    FordFulkerson ff = new FordFulkerson(flowNetwork, s, t);
    for (FlowEdge e : flowNetwork.adj(s)) {
      if (e.flow() != e.capacity()) {
        final Iterable<String> certificate = buildEliminationCertificate(ff);
        eliminationMap.put(team, certificate);
      }
    }
  }

  /**
   * We create a flow network and solve a maxflow problem in it.
   * In the network, feasible integral flows correspond to outcomes of the remaining schedule.
   * There are vertices corresponding to teams (other than team x) and to remaining divisional
   * games (not involving team x). Intuitively, each unit of flow in the network corresponds to
   * a remaining game. As it flows through the network from s to t, it passes from a game vertex,
   * say between teams i and j, then through one of the team vertices i or j,
   * classifying this game as being won by that team.
   *
   * @param team name
   * @return flow network
   */
  private FlowNetwork createFlowNetwork(String team) {
    int numberOfGames = numberOfGames();

    int s = 0;
    int v = numberOfTeams() + numberOfGames + 2;
    int t = v - 1;

    FlowNetwork flowNetwork = new FlowNetwork(v);
    int x = teams.get(team);
    int gameVertexIndex = 1;
    for (int i = 0; i < games.length; ++i) {
      for (int j = i + 1; j < games[i].length; ++j) {
        // We connect an artificial source vertex s to each game vertex i-j and set its capacity to g[i][j].
        // If a flow uses all g[i][j] units of capacity on this edge, then we interpret this as playing all of these games,
        // with the wins distributed between the team vertices i and j.
        flowNetwork.addEdge(new FlowEdge(s, gameVertexIndex, games[i][j]));
        // We connect each game vertex i-j with the two opposing team vertices to ensure that one of the two teams earns a win.
        // We do not need to restrict the amount of flow on such edges
        flowNetwork.addEdge(new FlowEdge(gameVertexIndex, numberOfGames + i + 1, Double.POSITIVE_INFINITY));
        flowNetwork.addEdge(new FlowEdge(gameVertexIndex, numberOfGames + j + 1, Double.POSITIVE_INFINITY));
        ++gameVertexIndex;
      }
      // Finally, we connect each team vertex to an artificial sink vertex t.
      // We want to know if there is some way of completing all the games so that team x ends up winning at least as many games as team i.
      // Since team x can win as many as w[x] + r[x] games, we prevent team i from winning more than that many games in total,
      // by including an edge from team vertex i to the sink vertex with capacity w[x] + r[x] - w[i].
      flowNetwork.addEdge(new FlowEdge(getTeamNetworkVertex(i), t, Math.max(0, wins[x] + remaining[x] - wins[i])));
    }

    return flowNetwork;
  }

  /**
   * Trivial elimination
   *
   * If the maximum number of games team x can win is less than the number of wins of some
   * other team i, then team x is trivially eliminated (as is Montreal in the example above).
   *
   * @return true if w[x] + r[x] < w[i], then team x is mathematically eliminated.
   */
  private boolean isTrivialEliminated(String team) {
    int x = teams.get(team);
    for (int i = 0; i < wins.length; ++i) {
      if (wins[x] + remaining[x] < wins[i]) {
        return true; // trivial elimination
      }
    }
    return false;
  }

  private void buildTrivialEliminationCertificate(String team) {
    if (eliminationMap.get(team) != null && eliminationMap.get(team).iterator().hasNext()) return;
    List<String> certificate = new ArrayList<>();
    int maxWins = wins(team) + remaining(team);
    for (Entry<String, Integer> e : teams.entrySet()) {
      if (wins[e.getValue()] > maxWins) {
        certificate.add(e.getKey());
      }
    }
    eliminationMap.put(team, certificate);
  }

  private Iterable<String> buildEliminationCertificate(FordFulkerson ff) {
    final List<String> certificate = new ArrayList<>();
    for (String team : teams()) {
      // The inCut() method in FordFulkerson takes a vertex as an argument
      // and returns true if that vertex is on the s-side of the mincut.
      if (ff.inCut(getTeamNetworkVertex(teams.get(team)))) {
        // one team in R will win at least w[x] + r[x] + 1 games
        certificate.add(team);
      }
    }
    return !certificate.isEmpty() ? certificate : null;
  }

  private int getTeamNetworkVertex(int teamIndex) {
    return teamIndex + numberOfGames() + 1;
  }

  private int numberOfGames() {
    return numberOfTeams() * (numberOfTeams() - 1) / 2;
  }

  private void validateTeam(String team) {
    if (team == null) {
      throw new IllegalArgumentException("arg is null");
    }
    if (!teams.containsKey(team)) {
      throw new IllegalArgumentException("no such team");
    }
  }

  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      } else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }
}
