package com.fitness.enterprise.management.customer.api

import com.fitness.enterprise.management.customer.model.CustomerDetails
import com.fitness.enterprise.management.customer.model.CustomersResponse
import retrofit2.Response
import retrofit2.http.*

interface CustomerApi {
    @GET("/api/customers/")
    suspend fun getAllCustomers() : Response<CustomersResponse>

    @GET("/api/customers/gym/{gymCode}/")
    suspend fun getAllCustomersByGymCode(@Path("gymCode") gymCode: String) : Response<CustomersResponse>

    @GET("/api/customers/{customerId}/")
    suspend fun getCustomerById(@Path("customerId") customerId: Int): Response<CustomerDetails>

    @GET("/api/customers/gym/{gymCode}/{customerId}/")
    suspend fun getCustomerByGymCodeAndId(@Path("gymCode") gymCode: String, @Path("customerId") customerId: Int): Response<CustomerDetails>

    @GET("/api/customers/gym/{gymCode}/contact/{contactNumber}/")
    suspend fun getCustomerByGymCodeAndContact(@Path("gymCode") gymCode: String, @Path("contactNumber") contactNumber: String): Response<CustomerDetails>

    @POST("/api/customers/")
    suspend fun createCustomer(@Body customerDetails: CustomerDetails) : Response<CustomerDetails>

    @PUT("/api/customers/{gymCode}/{contactNumber}/")
    suspend fun updateCustomer(@Path("gymCode") gymCode: String, @Path("contactNumber") contactNumber: String, @Body customerDetails: CustomerDetails) : Response<CustomerDetails>

    @PUT("/api/customers/{customerId}/")
    suspend fun updateCustomerToRegister(@Path("customerId") customerId: Int, @Body customerDetails: CustomerDetails) : Response<CustomerDetails>

}