package com.egeperk.hiltartbookpro.repo

import androidx.lifecycle.LiveData
import com.egeperk.hiltartbookpro.model.Art
import com.egeperk.hiltartbookpro.model.ImageResponse
import com.egeperk.hiltartbookpro.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art : Art)

    fun getArt  () : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>

}