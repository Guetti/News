## The News 
(c) Gustavo Patricio Szigethi Araya

The News Project

![Screenshot](Class%20diagram.png)
```
@startuml
package externals* #ffcccc {
    package org.threeten.bp {
        class ZonedDateTime {
            ...
        }
        class ZoneId {
            ...
        }
    }
    
    package net.openhft.hashing {
        class LongHashFunction {
            ...
        }
    }
    
    package com.github.javafaker.Faker{
        
        class Faker {
            ...
        }
    }
}


package cl.ucn.disc.dsm.gszigethi {


    
    package model #ccffcc {

        class News <<entity>> {
            - id: Long
            - title: String
            - source: String
            - author: String
            - url: String
            - urlImage: String
            - description: String
            - content: String
            + News(...)
            + getId(): Long
            + getTitle(): String
            + getSource(): String
            + getAuthor(): String
            + getUrl(): String
            + getUrlImage(): String
            + getDescription(): String
            + getContent(): String
            + getPublishedAt(): ZonedDateTime     
        }
        News *-> "1" ZonedDateTime : - publishedAt
        News ..> LongHashFunction : <<use>>
    }
    
    package services #ccccff {
        interface Contracts <<interface>> {
            + retrieveNews(size: int): List<News>
        }
        Contracts ..> News: <<use>>
        
        class ContractsImplFaker <<service>> {
            + ContractsImplFaker()
        }
        ContractsImplFaker ..|> Contracts
        ContractsImplFaker ..> ZonedDateTime : <<use>>
        ContractsImplFaker ..> ZoneId : <<use>>
        ContractsImplFaker ..> Faker : <<use>>
    }
}
@enduml

```