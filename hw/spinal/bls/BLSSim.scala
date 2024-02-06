package bls

import spinal.core._
import spinal.core.sim._

object BLSSim extends App {
  Config.sim.compile(BLS()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

    dut.io.a #= 5
    dut.io.b #= 10

    dut.io.m #= 107
    dut.io.m_prime #= 107

    dut.clockDomain.waitFallingEdge(5)
    /*
    dut.io.start #= true
    dut.clockDomain.waitRisingEdge()
    dut.io.start #= false

    dut.clockDomain.waitRisingEdgeWhere(dut.io.done.toBoolean)
     */

    println(dut.io.result.toBigInt)

    dut.clockDomain.waitRisingEdge(5)

  }
}
