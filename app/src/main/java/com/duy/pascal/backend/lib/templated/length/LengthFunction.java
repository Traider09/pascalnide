/*
 *  Copyright 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.pascal.backend.lib.templated.length;


import android.util.Log;

import com.duy.pascal.backend.exceptions.ParsingException;
import com.duy.pascal.backend.lib.templated.abstract_class.TemplatePascalFunction;
import com.duy.pascal.backend.linenumber.LineInfo;
import com.duy.pascal.backend.pascaltypes.ArgumentType;
import com.duy.pascal.backend.pascaltypes.ArrayType;
import com.duy.pascal.backend.pascaltypes.BasicType;
import com.duy.pascal.backend.pascaltypes.DeclaredType;
import com.duy.pascal.backend.pascaltypes.RuntimeType;
import com.duy.pascal.backend.pascaltypes.rangetype.IntegerSubrangeType;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.ReturnsValue;

public class LengthFunction implements TemplatePascalFunction {

    private static final String TAG = "LengthFunction";
    private ArgumentType[] argumentTypes = {
            new RuntimeType(
                    new ArrayType<>(BasicType.anew(Object.class), new IntegerSubrangeType(0, -1)),
                    true),};

    @Override
    public String name() {
        return "length";
    }

    @Override
    public FunctionCall generateCall(LineInfo line, ReturnsValue[] arguments,
                                     ExpressionContext f) throws ParsingException {
        ReturnsValue array = arguments[0];
        Log.d(TAG, "generateCall: ");
        return new LengthCall(array, line);
    }

    @Override
    public FunctionCall generatePerfectFitCall(LineInfo line, ReturnsValue[] values, ExpressionContext f) throws ParsingException {
        Log.d(TAG, "generatePerfectFitCall: ");
        return generateCall(line, values, f);
    }

    @Override
    public ArgumentType[] argumentTypes() {
        return argumentTypes;
    }

    @Override
    public DeclaredType returnType() {
        return BasicType.Integer;
    }

}