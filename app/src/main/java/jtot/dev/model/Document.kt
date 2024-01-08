package jtot.dev.model

import java.io.Serializable

abstract class Document : Serializable {
    abstract val title: String
    abstract val content: String
}
