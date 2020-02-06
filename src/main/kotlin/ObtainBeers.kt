fun List<Pub>.removeDuplicates() = this
    .sortedByDescending { it.createTS }
    .distinctBy { it.id }