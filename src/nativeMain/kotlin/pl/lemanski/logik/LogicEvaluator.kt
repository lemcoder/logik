package pl.lemanski.logik

import pl.lemanski.logik.antlr.LogicBaseVisitor
import pl.lemanski.logik.antlr.LogicParser

class LogicEvaluator(private val variableValues: Map<String, Boolean>) : LogicBaseVisitor<Boolean>() {

    override fun visitExpr(ctx: LogicParser.ExprContext): Boolean {
        return when {
            ctx.IMPL() != null                           -> !visit(ctx.expr(0)!!) || visit(ctx.expr(1)!!)   // p -> q <-> !p || q
            ctx.IFF() != null                            -> visit(ctx.expr(0)!!) == visit(ctx.expr(1)!!)    // p <-> q is true if both sides are equal
            ctx.AND() != null                            -> visit(ctx.expr(0)!!) && visit(ctx.expr(1)!!)
            ctx.OR() != null                             -> visit(ctx.expr(0)!!) || visit(ctx.expr(1)!!)
            ctx.NOT() != null                            -> !visit(ctx.expr(0)!!)
            ctx.VAR() != null                            -> variableValues[ctx.VAR()!!.text]!!     // Get the variable's value from the map
            ctx.LPAREN() != null && ctx.RPAREN() != null -> visit(ctx.expr(0)!!) // Evaluate inside parentheses
            else                                         -> false
        }
    }

    override fun defaultResult(): Boolean = false
}