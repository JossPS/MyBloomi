package com.bloomi.demo.repositories

import com.bloomi.demo.models.entities.Foundation
import com.bloomi.demo.models.responses.ActivityResponse
import org.springframework.data.mongodb.repository.MongoRepository

interface FoundationRepository : MongoRepository<Foundation, String>