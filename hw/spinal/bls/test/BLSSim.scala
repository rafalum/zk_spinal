package bls.test

import bls.src._
import spinal.core._
import spinal.core.sim._


object BLSSim extends App {
  Config.sim.compile(BLS()).doSim { dut =>
    // Fork a process to generate the reset and the clock on the dut
    dut.clockDomain.forkStimulus(period = 10)

    dut.io.i_val #= false
    dut.io.i_dat_a #= BigInt("0", 16)
    dut.io.i_dat_b #= BigInt("0", 16)
    dut.io.i_rdy #= true
    dut.io.i_ctl #= 0

    dut.clockDomain.waitFallingEdge(20)

    dut.io.i_val #= true
    dut.io.i_dat_a #= BigInt("14d7734ebf919669f0c39adb606363703d2ea3c5389f8e5c6a203bd5b00724bc136fc44ee9c2b6439ce40bb0ba18f0a", 16)
    dut.io.i_dat_b #= BigInt("1f55daac5e9b210fc4aab940e88a2e4b860cb015d5d1906d6f17199a4773db1b6354cb21fe1764686d20d608bf241db", 16)

    dut.clockDomain.waitFallingEdge()

    dut.io.i_val #= true
    dut.io.i_dat_a #= BigInt("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", 16)
    dut.io.i_dat_b #= BigInt("16c0347b8b7854a9a36a29475ea577e284c1ae5f0c7595c4c65243d06c36a501564867f4d29e7e77203602db2771346", 16)

    dut.clockDomain.waitFallingEdge()

    dut.io.i_val #= true
    dut.io.i_dat_a #= BigInt("1b4cce39cf42503d9b29354c24164f26623e234f21e45c68ab3baa70925d56b29faec805f368134e8995eb25a7fd375", 16)
    dut.io.i_dat_b #= BigInt("0a8dc9f79ccc971c87418a84587e395e3f4e3029b2e25341a7048829b3d1e6ef49e9f4661a592fd657437e8e8dde19c", 16)

    dut.clockDomain.waitFallingEdge()

    dut.io.i_val #= false
    dut.io.i_dat_a #= BigInt("0", 16)
    dut.io.i_dat_b #= BigInt("0", 16)

    dut.clockDomain.waitRisingEdge(50)

    dut.clockDomain.waitFallingEdge()

    dut.io.i_val #= true
    dut.io.i_dat_a #= BigInt("14d7734ebf919669f0c39adb606363703d2ea3c5389f8e5c6a203bd5b00724bc136fc44ee9c2b6439ce40bb0ba18f0a", 16)
    dut.io.i_dat_b #= BigInt("1f55daac5e9b210fc4aab940e88a2e4b860cb015d5d1906d6f17199a4773db1b6354cb21fe1764686d20d608bf241db", 16)

    dut.clockDomain.waitFallingEdge()

    dut.io.i_val #= false
    dut.io.i_dat_a #= BigInt("0", 16)
    dut.io.i_dat_b #= BigInt("0", 16)


    dut.clockDomain.waitRisingEdge(50)

  }
}
