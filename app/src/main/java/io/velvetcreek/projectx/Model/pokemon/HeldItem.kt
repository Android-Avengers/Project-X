package io.velvetcreek.projectx.Model.pokemon

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)