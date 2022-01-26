package io.velvetcreek.projectx.Model

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)