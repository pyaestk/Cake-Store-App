package com.example.shoppingapp.data.repository

import com.example.shoppingapp.data.remote.ItemRemoteDataSource
import com.example.shoppingapp.data.remote.RemoteDatasource
import com.example.shoppingapp.data.util.toModel
import com.example.shoppingapp.domain.model.CategoryModel
import com.example.shoppingapp.domain.model.ItemModel
import com.example.shoppingapp.domain.repository.CategoryRepository
import com.example.shoppingapp.domain.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(
    private val remoteDatasource: RemoteDatasource,
    private val itemRemoteDataSource: ItemRemoteDataSource
): CategoryRepository {
    override fun loadItemByCategory(categoryId: Int): Flow<Response<List<ItemModel>>> {
        return itemRemoteDataSource.loadItemByCategory(categoryId).map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }

    override fun loadCategories(): Flow<Response<List<CategoryModel>>> {
        return remoteDatasource.loadCategories().map { response ->
            when (response) {
                is Response.Success -> Response.Success(response.data?.map { it.toModel() })
                is Response.Error -> Response.Error(response.message ?: "Unknown error")
                is Response.Loading -> Response.Loading()
            }
        }
    }

}