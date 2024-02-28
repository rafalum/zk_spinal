package bls.src

import spinal.core.sim.SimConfig
import spinal.core.{ClockDomainConfig, HIGH, SpinalConfig}

object Config {
  def spinal = SpinalConfig(
    targetDirectory = "hw/gen",
    defaultConfigForClockDomains = ClockDomainConfig(
      resetActiveLevel = HIGH
    ),
    onlyStdLogicVectorAtTopLevelIo = true
  )

  def sim = SimConfig.withConfig(spinal).withWave
}
