import java.util.*;

public class NfaToDfa {
    static List<String> states = List.of("q0", "q1", "q2", "q3");
    static List<String> alphas = List.of("a", "b");
    static String e = "q0";
    static String f = "q3";
    static Map<String, Map<String, String>> rules = new HashMap<>();

    public static void main(String[] args) {
        rules.put("q0", Map.of("a", "q1", "b", "q0"));
        rules.put("q1", Map.of("b", "q1 q2"));
        rules.put("q2", Map.of("a", "q2", "b", "q3"));
        rules.put("q3", Map.of(F," "));
        buildDFA();
    }

    public static void buildDFA() {
        var dfaRules = new HashMap<>(rules);

        for (var stateRules : rules.entrySet()) {
            for (var rule : stateRules.getValue().entrySet()) {
                if (!dfaRules.containsKey(rule.getValue())) {
                    String newState = rule.getValue();
                    Map<String, String> newRules = new HashMap<>();
                    for (String word : alphas) {
                        StringBuilder endState = new StringBuilder();
                        for (String state : newState.split(" ")) {
                            var s = rules.get(state).get(word);
                            if (s != null) endState.append(" ").append(s);
                        }

                        var strState = endState.toString().trim();
                        if (!strState.isEmpty() && !strState.isBlank())
                            newRules.put(word, strState);
                    }

                    dfaRules.put(newState, newRules);
                }
            }
        }

        for (var stateRules : dfaRules.entrySet()) {
            for (var rule : stateRules.getValue().entrySet()) {
                System.out.printf("([%s], %s) -> %s\n", stateRules.getKey(), rule.getKey(), rule.getValue());
            }
        }
    }
}