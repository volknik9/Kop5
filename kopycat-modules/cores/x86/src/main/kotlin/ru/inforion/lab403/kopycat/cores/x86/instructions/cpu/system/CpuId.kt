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
package ru.inforion.lab403.kopycat.cores.x86.instructions.cpu.system

import ru.inforion.lab403.kopycat.cores.base.exceptions.GeneralException
import ru.inforion.lab403.kopycat.cores.x86.hardware.systemdc.Prefixes
import ru.inforion.lab403.kopycat.cores.x86.instructions.AX86Instruction
import ru.inforion.lab403.kopycat.modules.cores.x86Core


class CpuId(core: x86Core, opcode: ByteArray, prefs: Prefixes):
        AX86Instruction(core, Type.VOID, opcode, prefs) {
    override val mnem = "cpuid"

    override fun execute() {
        // TODO: Implement configuration of AX86 device and CPUID
        val case = core.cpu.regs.eax
        when (case) {
            0L -> {
                core.cpu.regs.eax = 0x1
                core.cpu.regs.ebx = 0x756e6547
                core.cpu.regs.edx = 0x49656e69
                core.cpu.regs.ecx = 0x6c65746e
            }
            1L -> {
                log.severe { "CPUID instruction not implemented -> execution may be wrong!" }
                core.cpu.regs.eax = 0x0  // Original OEM processor
                core.cpu.regs.ebx = 0x0
                core.cpu.regs.ecx = 0x0
                core.cpu.regs.edx = 0x4F4 // For amd elan 520
            }
            else -> throw GeneralException("Incorrect argument in CpuId insn")
        }
    }
}