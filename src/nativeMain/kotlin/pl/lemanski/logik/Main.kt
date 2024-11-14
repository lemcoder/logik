package pl.lemanski.logik

import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import pl.lemanski.logik.antlr.*

fun main(args: Array<String>) {
    val expression = args.getOrNull(0) ?: run {
        println(
            """
            type in the logic expression. Allowed symbols are:
            '->'    Implication
            '<->'   Bi-conditional
            '||'    Disjunction
            '&&'    Conjunction
            '!'     Negation
            '(' ')' Parentheses
            p,q...  Variables
        """.trimIndent()
        )
        return
    }

    val lexer = LogicLexer(CharStreams.fromString(expression))
    val tokens = CommonTokenStream(lexer)
    val parser = LogicParser(tokens)
    val parseTree = parser.expr()

    val variableCollector = VariableCollector()
    val variables = variableCollector.visit(parseTree)

    val isTautologyResult = isTautology(parseTree, variables)
    if (isTautologyResult) {
        println("Expression\n\n    $expression\n\nis a tautology\n")
    } else {
        println("Expression\n\n    $expression\n\nis NOT a tautology\n")
    }
}
