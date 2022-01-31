package com.egeperk.hiltartbookpro.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.egeperk.hiltartbookpro.model.Art
import com.egeperk.hiltartbookpro.model.ImageResponse
import com.egeperk.hiltartbookpro.util.Resource

class FakeArtRepositoryTest : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }

}