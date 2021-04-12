all: run

clean:
	rm -f out/Main.jar out/MandelbrotAlgo.jar

out/Main.jar: out/parcs.jar src/Main.java
	@javac -cp out/parcs.jar src/Main.java
	@jar cf out/Main.jar -C src Main.class
	@rm -f src/Main.class

out/MandelbrotAlgo.jar: out/parcs.jar src/MandelbrotAlgo.java src/Complex.java
	@javac -cp out/parcs.jar src/MandelbrotAlgo.java src/Complex.java
	@jar cf out/MandelbrotAlgo.jar -C src MandelbrotAlgo.class -C src Complex.class
	@rm -f src/MandelbrotAlgo.class src/Complex.class
	
MandelbrotAlgo.jar: out/parcs.jar src/*.java
	@javac -cp out/parcs.jar src/*.java
	@jar cf out/MandelbrotAlgo.jar -C src .
	@rm -f src/*.class

build: MandelbrotAlgo.jar

run: out/MandelbrotAlgo.jar
	@cd out && java -cp 'parcs.jar:MandelbrotAlgo.jar' Main