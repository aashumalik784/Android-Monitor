#include <jni.h>
#include <string>
#include <fstream>
#include <sstream>
#include <sys/sysinfo.h>
#include <chrono>

std::string getCpuInfo() {
    std::ifstream file("/proc/cpuinfo");
    std::string line;
    int cores = 0;
    while (std::getline(file, line)) {
        if (line.find("processor") == 0) cores++;
    }
    std::ostringstream oss;
    oss << "CPU Cores: " << cores;
    return oss.str();
}

std::string getMemInfo() {
    struct sysinfo info;
    sysinfo(&info);
    long totalRamMB = info.totalram / (1024 * 1024);
    long freeRamMB = info.freeram / (1024 * 1024);
    std::ostringstream oss;
    oss << "Total RAM: " << totalRamMB << " MB\n";
    oss << "Free RAM: " << freeRamMB << " MB";
    return oss.str();
}

long long runBenchmark() {
    auto start = std::chrono::high_resolution_clock::now();
    long long sum = 0;
    for (long long i = 0; i < 10000000; ++i) sum += i * i;
    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> elapsed = end - start;
    return (long long)elapsed.count();
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_androidmonitor_MainActivity_getSystemData(JNIEnv* env, jobject) {
    std::string cpu = getCpuInfo();
    std::string mem = getMemInfo();
    std::ostringstream oss;
    oss << "--- ANDROID MONITOR ---\n" << cpu << "\n" << mem << "\nOS: Linux Kernel";
    return env->NewStringUTF(oss.str().c_str());
}

extern "C" JNIEXPORT jlong JNICALL
Java_com_androidmonitor_MainActivity_runSpeedTest(JNIEnv* env, jobject) {
    return runBenchmark();
}
