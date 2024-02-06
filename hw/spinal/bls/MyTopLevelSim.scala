package projectname

import spinal.core._
import spinal.core.sim._

object MyTopLevelSim extends App {
  Config.sim.compile(MyTopLevel()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

    dut.io.a := IntToBits(5)
    dut.io.b := IntToBits(10)

    dut.io.m := IntToBits(107)
    dut.io.m_prime := IntToBits(107)

    dut.clockDomain.waitRisingEdge(5)

    dut.io.start := True

    dut.clockDomain.waitRisingEdgeWhere(dut.io.done.toBoolean)

    println(dut.io.result.asUInt)

    dut.clockDomain.waitRisingEdge(5)

  }
}
