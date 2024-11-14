package pl.lemanski.logik

import pl.lemanski.logik.antlr.LogicParser

fun isTautology(parseTree: LogicParser.ExprContext, variables: Set<String>): Pair<Boolean, List<Map<String, Boolean>>> {
    val variableList = variables.toList()
    val numberOfCombinations = 1 shl variableList.size  // 2^n combinations for n variables
    val counterexamples = mutableListOf<Map<String, Boolean>>()  // Store each counterexample here

    for (i in 0 until numberOfCombinations) {
        // Create a truth assignment based on the bits of `i`
        val truthAssignment = mutableMapOf<String, Boolean>()
        for (j in variableList.indices) {
            truthAssignment[variableList[j]] = (i and (1 shl j)) != 0
        }

        val evaluator = LogicEvaluator(truthAssignment)
        if (!evaluator.visit(parseTree)) {
            counterexamples.add(truthAssignment)
        }
    }

    // If no counterexamples, it’s a tautology
    return Pair(counterexamples.isEmpty(), counterexamples)
}