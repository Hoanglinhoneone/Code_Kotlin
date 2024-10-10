class Japanese {
    private var id : String
    private var romaji : String
    private var kata: String
    private var groupe: String
    private var example: String

    constructor(id: String, romaji: String, kata: String, groupe: String, example: String) {
        this.id = id
        this.romaji = romaji
        this.kata = kata
        this.groupe = groupe
        this.example = example
    }

    override fun toString(): String {
        return "Japanese(id='$id', romaji='$romaji', kata='$kata', groupe='$groupe', example='$example')"
    }

}