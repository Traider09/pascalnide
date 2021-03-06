package com.duy.pascal.backend.ast.runtime_value.value.cloning;

import android.support.annotation.NonNull;

import com.duy.pascal.backend.ast.codeunit.RuntimeExecutableCodeUnit;
import com.duy.pascal.backend.ast.expressioncontext.CompileTimeContext;
import com.duy.pascal.backend.ast.expressioncontext.ExpressionContext;
import com.duy.pascal.backend.ast.variablecontext.VariableContext;
import com.duy.pascal.backend.ast.runtime_value.value.AssignableValue;
import com.duy.pascal.backend.ast.runtime_value.value.RuntimeValue;
import com.duy.pascal.backend.ast.variablecontext.ContainsVariables;
import com.duy.pascal.backend.linenumber.LineInfo;
import com.duy.pascal.backend.parse_exception.ParsingException;
import com.duy.pascal.backend.runtime_exception.RuntimePascalException;
import com.duy.pascal.backend.declaration.lang.types.RuntimeType;

import static com.duy.pascal.backend.utils.NullSafety.zReturn;

public class CloneableObjectCloner implements RuntimeValue {
    private RuntimeValue r;

    public CloneableObjectCloner(RuntimeValue r) {
        this.r = r;
    }

    @Override
    public RuntimeType getRuntimeType(ExpressionContext f) throws ParsingException {
        return r.getRuntimeType(f);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @NonNull
    @Override
    public Object getValue(VariableContext f, RuntimeExecutableCodeUnit<?> main)
            throws RuntimePascalException {
        Object value = r.getValue(f, main);
        if (value instanceof ContainsVariables) {
            ContainsVariables c = (ContainsVariables) value;
            return zReturn(c.clone());
        }
        return value;
    }

    @NonNull
    @Override
    public LineInfo getLineNumber() {
        return r.getLineNumber();
    }

    @Override
    public void setLineNumber(LineInfo lineNumber) {

    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        return r.compileTimeValue(context);
    }

    @Override
    public RuntimeValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return new CloneableObjectCloner(r.compileTimeExpressionFold(context));
    }

    @Override
    public AssignableValue asAssignableValue(ExpressionContext f) {
        return null;
    }
}