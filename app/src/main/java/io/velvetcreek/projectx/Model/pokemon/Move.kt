package io.velvetcreek.projectx.Model.pokemon

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)