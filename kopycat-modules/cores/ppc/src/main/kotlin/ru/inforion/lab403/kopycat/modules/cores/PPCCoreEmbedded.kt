/*
 *
 * This file is part of Kopycat emulator software.
 *
 * Copyright (C) 2020 INFORION, LLC
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Non-free licenses may also be purchased from INFORION, LLC, 
 * for users who do not want their programs protected by the GPL. 
 * Contact us for details kopycat@inforion.ru
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package ru.inforion.lab403.kopycat.modules.cores

import ru.inforion.lab403.common.extensions.MHz
import ru.inforion.lab403.kopycat.cores.base.GenericSerializer
import ru.inforion.lab403.kopycat.cores.base.common.Module
import ru.inforion.lab403.kopycat.cores.ppc.exceptions.IPPCExceptionHolder
import ru.inforion.lab403.kopycat.cores.ppc.exceptions.PPCExceptionHolder_Embedded
import ru.inforion.lab403.kopycat.cores.ppc.hardware.peripheral.TimeBase
import ru.inforion.lab403.kopycat.cores.ppc.hardware.processors.PPCCOP
import ru.inforion.lab403.kopycat.cores.ppc.hardware.processors.PPCCPU
import ru.inforion.lab403.kopycat.cores.ppc.hardware.processors.systems.PPCCPU_Embedded



abstract class PPCCoreEmbedded(parent: Module,
                               name: String,
                               frequency: Long,
                               exceptionHolder: IPPCExceptionHolder = PPCExceptionHolder_Embedded,
                               optionalCpu: ((PPCCore, String) -> PPCCPU)? = ::PPCCPU_Embedded,
                               optionalCop: ((PPCCore, String) -> PPCCOP)? = null):
        PPCCore(parent, name, frequency, exceptionHolder, optionalCpu, optionalCop) {

    @Suppress("LeakingThis")
    val timebase = TimeBase(this, "timebase", 1.MHz)

    override fun initRoutine() {
        super.initRoutine()
        timebase.connect()
    }

    override fun serialize(ctxt: GenericSerializer): Map<String, Any> {
        return super.serialize(ctxt) + mapOf(
                "ppccore" to super.serialize(ctxt),
                "timebase" to timebase.serialize(ctxt)
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(ctxt: GenericSerializer, snapshot: Map<String, Any>) {
        super.deserialize(ctxt, snapshot["ppccore"] as Map<String, String>)
        timebase.deserialize(ctxt, snapshot["timebase"] as Map<String, String>)
    }

}