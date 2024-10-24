package request.retrofit

import model.minna.commom.AudioFile
import model.minna.*

class RetrofitMinna() {
    private val retrofitMazzi = RetrofitMazzi()
    private val apiService = retrofitMazzi.getApiService()

    fun getVocabularyList(i : Int, callback: (List<Vocabulary>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getVocabulary(index) }, i, callback)
    }

    fun getGrammarList(i : Int, callback: (List<Grammar>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getGrammar(index) }, i, callback)
    }

    fun getKaiwaList(i : Int, callback: (List<Kaiwa>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getKaiWa(index) }, i, callback)
    }

    fun getReferenceList(i : Int, callback: (List<Reference>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getReference(index) }, i, callback)
    }

    fun getMondaiList(i : Int, callback: (List<Mondai>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getMondai(index) }, i, callback)
    }

    fun getBunkeiList(i : Int, callback: (List<Bunkei>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getBunkei(index) }, i, callback)
    }

    fun getReibunList(i : Int, callback: (List<Reibun>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getReibun(index)}, i, callback)
    }
    fun getReibunListt(i: Int, callback: (List<Vocabulary>?) -> Unit) {
        retrofitMazzi.getListOneParam({ index -> this.apiService.getVocabulary(index) }, i, callback)
    }

    fun getAudioFile(i: Int, type: String, callback: (List<AudioFile>?) -> Unit) {
        retrofitMazzi.getListTwoParam({ index, kind -> this.apiService.getAudioFile(index, kind) },i, type, callback)
    }

}