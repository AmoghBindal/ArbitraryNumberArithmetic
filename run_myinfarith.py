import subprocess
import sys
import os

def build_project():
    print("Building the project using Ant...")
    result = subprocess.run(["ant", "jar"])
    if result.returncode != 0:
        print("Build failed.")
        sys.exit(1)
    print("Build successful.\n")

def run_jar(arguments):
    jar_path = os.path.join("dist", "MyInfArith.jar")
    if not os.path.exists(jar_path):
        print(f"JAR not found at {jar_path}. Make sure the build succeeded.")
        sys.exit(1)

    cmd = ["java", "-jar", jar_path] + arguments
    print(f"Running: {' '.join(cmd)}\n")
    subprocess.run(cmd)

if __name__ == "__main__":
    if len(sys.argv) != 5:
        print("Usage: python run_myinfarith.py <int|float> <add|sub|mul|div> <operand1> <operand2>")
        sys.exit(1)

    build_project()
    run_jar(sys.argv[1:])
