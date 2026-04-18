# Build and Run script for AgroMind AI

# Create necessary directories
New-Item -ItemType Directory -Force -Path lib | Out-Null
New-Item -ItemType Directory -Force -Path target\classes | Out-Null

# Download FlatLaf dependency if it doesn't exist
$jarPath = "lib\flatlaf-3.4.1.jar"
if (-Not (Test-Path $jarPath)) {
    Write-Host "Downloading FlatLaf UI Library..."
    Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1/flatlaf-3.4.1.jar" -OutFile $jarPath
}

# Compile Java sources
Write-Host "Cleaning and Compiling Java sources (Target: Java 21)..."
Remove-Item -Recurse -Force target\classes\* -ErrorAction SilentlyContinue
$files = Get-ChildItem -Recurse -Filter *.java src\main\java | Select-Object -ExpandProperty FullName
javac --release 21 -cp $jarPath -d target\classes $files

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed. Ensure you have JDK 21 or higher installed."
    exit $LASTEXITCODE
}

# Run the application
Write-Host "Starting AgroMind AI..."
java -cp "target\classes;$jarPath" com.agromind.AgroMindApp
