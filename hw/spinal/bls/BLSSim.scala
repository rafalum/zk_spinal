package bls

import spinal.core._
import spinal.core.sim._

object BLSSim extends App {
  Config.sim.compile(BLS()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

    dut.io.a #= 12
    dut.io.b #= 10

    dut.clockDomain.waitFallingEdge()

    println(dut.io.result.toBigInt)

    dut.clockDomain.waitRisingEdge(5)

  }
}
