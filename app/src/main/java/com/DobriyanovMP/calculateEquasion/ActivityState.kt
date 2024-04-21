package com.DobriyanovMP.calculateEquasion

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class ActivityState(
    var AllEquationsCount: Int,
    var CorrectEquationsCount : Int,
    var WrongEquationsCount: Int,
    var LastFirstOperand: Int,
    var LastSecondOperand: Int,
    var LastOperation: String,
    var StartButtonState: Boolean,
    var EquationColor: Int
): Parcelable