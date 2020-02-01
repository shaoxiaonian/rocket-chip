// See LICENSE.SiFive for license details.

package freechips.rocketchip.stage

import firrtl.options.Shell

trait RocketChipCli { this: Shell =>

  parser.note("Rocket Chip Compiler Options")
  Seq(
    TargetDirectoryAnnotation,
    TopPackageAnnotation,
    TopClassAnnotation,
    ConfigPackageAnnotation,
    ConfigAnnotation,
    OutputBaseNameAnnotation,
  )
    .foreach(_.addOptions(parser))

}
