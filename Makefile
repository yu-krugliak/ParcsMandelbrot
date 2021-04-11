all: run

clean:
	rm -f out/Main.jar out/MandelbrotAlgo.jar

out/Main.jar: out/parcs.jar src/Main.java
	@javac -cp out/parcs.jar src/Main.java
	@jar cf out/Main.jar -C src Main.class
	@rm -f src/Main.class

out/MandelbrotAlgo.jar: out/parcs.jar src/MandelbrotAlgo.java
	@javac -cp out/parcs.jar src/MandelbrotAlgo.java
	@jar cf out/MandelbrotAlgo.jar -C src MandelbrotAlgo.class
	@rm -f src/MandelbrotAlgo.class

build: out/Main.jar out/MandelbrotAlgo.jar

run: out/Main.jar
	@cd out && java -cp 'parcs.jar:Main.jar' Main