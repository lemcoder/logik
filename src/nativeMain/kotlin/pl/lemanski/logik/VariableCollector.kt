package pl.lemanski.logik

import pl.lemanski.logik.antlr.LogicBaseVisitor
import pl.lemanski.logik.antlr.LogicParser

class VariableCollector : LogicBaseVisitor<Set<String>>() {
    override fun visitExpr(ctx: LogicParser.ExprContext): Set<String> {
        val vars = mutableSetOf<String?>()
        if (ctx.VAR() != null) {
            vars.add(ctx.VAR()?.text)
        }
        ctx.children?.forEach { child ->
            vars.addAll(visit(child))
        }

        return vars.mapNotNull { it }.toSet()
    }

    override fun defaultResult(): Set<String> = setOf()
}