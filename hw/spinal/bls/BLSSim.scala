package bls

import bls.dataTypes.JacobianPoint
import spinal.core._
import spinal.core.sim._

object BLSSim extends App {
  Config.sim.compile(BLS()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

    dut.io.c.ready #= true

    dut.io.a.payload.x #= 6
    dut.io.a.payload.y #= 4
    dut.io.a.payload.z #= 1
    dut.io.a.valid #= false

    dut.io.b.payload.x #= 18
    dut.io.b.payload.y #= 20
    dut.io.b.payload.z #= 1
    dut.io.b.valid #= false

    dut.clockDomain.waitFallingEdge()

    dut.io.a.valid #= true
    dut.io.b.valid #= true

    dut.clockDomain.waitFallingEdge()

    dut.io.a.valid #= false
    dut.io.b.valid #= false

    dut.clockDomain.waitRisingEdge(20)

  }
}
