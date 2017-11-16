package fr.antproject.utils

enum class ContourRetrievalMode(val value: Int) {
    EXTERNAL(0),
    LIST(1),
    CCOMP(2),
    TREE(3),
    FLOOD_FILL(4)
}

enum class ContourApproxMethod(val value: Int) {
    NONE(1),
    SIMPLE(2),
    TC89_L1(3),
    TC89_KCOS(4)
}