cmake_minimum_required(VERSION 3.22.1)

project("androidmonitor")

add_library(androidmonitor SHARED
        native-lib.cpp)

find_library(log-lib log)

target_link_libraries(androidmonitor
        ${log-lib})
