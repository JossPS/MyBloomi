package com.bloomi.demo.exceptions.exceptions

import org.apache.logging.log4j.message.Message

class DuplicateUser (message: String): RuntimeException(message)