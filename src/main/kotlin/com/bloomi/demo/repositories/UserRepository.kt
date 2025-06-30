package com.bloomi.demo.repositories

import com.bloomi.demo.models.entities.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : MongoRepository<User, String>