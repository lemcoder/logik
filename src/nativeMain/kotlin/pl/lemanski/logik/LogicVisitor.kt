package pl.lemanski.logik

import pl.lemanski.logik.antlr.LogicBaseVisitor
import pl.lemanski.logik.antlr.LogicParser

class LogicPrettyPrinter : LogicBaseVisitor<String>() {

    override fun visitExpr(ctx: LogicParser.ExprContext): String {
        return when {
            ctx.IMPL() != null                           -> "${ctx.expr(0)?.let { visit(it) }} -> ${ctx.expr(1)?.let { visit(it) }}"
            ctx.IFF() != null                            -> "${ctx.expr(0)?.let { visit(it) }} <-> ${ctx.expr(1)?.let { visit(it) }}"
            ctx.AND() != null                            -> "${ctx.expr(0)?.let { visit(it) }} && ${ctx.expr(1)?.let { visit(it) }}"
            ctx.OR() != null                             -> "${ctx.expr(0)?.let { visit(it) }} || ${ctx.expr(1)?.let { visit(it) }}"
            ctx.NOT() != null                            -> "!${ctx.expr(0)?.let { visit(it) }}"
            ctx.VAR() != null                            -> ctx.VAR()?.text ?: ""
            ctx.LPAREN() != null && ctx.RPAREN() != null -> "(${ctx.expr(0)?.let { visit(it) }})"
            else                                         -> ""
        }
    }

    override fun defaultResult(): String = ""
}