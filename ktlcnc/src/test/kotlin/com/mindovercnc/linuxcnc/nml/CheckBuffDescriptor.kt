package com.mindovercnc.linuxcnc.nml

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.regex.Matcher
import java.util.regex.Pattern

class CheckBuffDescriptor(private val nativeDir: File) {
    private val buffDescriptor: BuffDescriptor = BuffDescriptorV29()
    private val bePat = Pattern.compile("\\[\\s*(\\d+)\\]\\s+(\\S+)")
    private val offsets = mutableMapOf<String, Int>()

    fun check(fileName: String) {
        readNativeDescription(fileName)
        println("------------Checking buffer descriptor----------")
        var allOk = true
        println("---The native code has: ${offsets.size} entries")
        println("---The 'Key' enum has: ${buffDescriptor.entries.size} entries")
        for (anEntry in offsets.entries) {
            Key.fromString(anEntry.key)?.let { key ->
                buffDescriptor.entries[key]?.let {
                    if (it.startOffset != anEntry.value) {
                        allOk = false
                        System.err.println(
                            "Offsets are not matching for key: ${key}, expected: ${anEntry.value}, actual: ${it.startOffset}"
                        )
                    }
                }
                    ?: run {
                        allOk = false
                        System.err.println(
                            "The key: [$key], does not have a decoding value associated! It should have offset: [${anEntry.value}]"
                        )
                    }
            }
                ?: run {
                    allOk = false
                    System.err.println(
                        "The native keyString ${anEntry.key} is not associated to any Key enum entry!"
                    )
                }
        }
        System.err.flush()
        if (allOk) {
            println("------------Successfully checked----------------")
        }
    }

    private fun readNativeDescription(fileName: String) {
        val dumpBufDesc = File(nativeDir, fileName)
        if (!dumpBufDesc.exists() || !dumpBufDesc.canExecute()) {
            throw IllegalArgumentException("${dumpBufDesc.absolutePath} not found")
        }
        if (!dumpBufDesc.exists() || !dumpBufDesc.isFile || !dumpBufDesc.canExecute()) {
            System.err.println("could not find or execute: " + dumpBufDesc.absolutePath)
            System.exit(-1)
        }
        val pb = ProcessBuilder(dumpBufDesc.absolutePath, "doIt")
        var p: Process? = null
        var input: BufferedReader? = null
        var line: String?
        try {
            pb.directory(dumpBufDesc.parentFile)
            pb.redirectErrorStream(true)
            p = pb.start()
            input = BufferedReader(InputStreamReader(p.inputStream))
            var m: Matcher
            while (input.readLine().also { line = it } != null) {
                m = bePat.matcher(line)
                if (m.find(0)) {
                    offsets[m.group(2)] = m.group(1).toInt()
                }
            }
            println(offsets)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (p != null) {
                try {
                    p.waitFor()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            if (input != null) {
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
