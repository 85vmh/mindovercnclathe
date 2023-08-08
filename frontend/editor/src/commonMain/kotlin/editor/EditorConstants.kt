package editor

object EditorConstants {
  val gcodeCharacters =
    charArrayOf(
      'G',
      'M',
      'X',
      'Y',
      'Z',
      'I',
      'J',
      'K',
      'F',
      'S',
      'T',
      'P',
      'L',
      'Q',
      'N',
      'E',
      'D',
      'R',
      'B',
      'O',
      'A',
    )

  val keywords =
    arrayOf(
      "return",
      "if",
      "else",
      "endif",
      "while",
      "endwhile",
      "sub",
      "endsub",
      "repeat",
      "endrepeat",
    )

  val punctuation = arrayOf(":", "=", "\"", "[", "]", "{", "}")
}
