all: compile

compile: clean
	mkdir -p int/ast_classes bin src/java_cup/anttask src/java_cup/runtime
	cp src/ast_classes/src/*.java int/ast_classes/
	rm int/ast_classes/Main.java
	cp src/*.d bin/
	cp src/NSScanner.flex int/
	cp src/NSParser.cup int/
	cp src/java_cup.jar int/
	cp src/ast_classes/src/Main.java int/
	java -jar src/java_cup.jar -parser NSParser -destdir int/ int/NSParser.cup
	java -jar src/jflex.jar int/NSScanner.flex -d int/
	javac int/sym.java
	javac int/ast_classes/*.java
	javac -classpath ./int/java_cup.jar:./int:./int/ast_classes int/NSParser.java
	javac -classpath ./int/java_cup.jar:./int:./int/ast_classes int/NSScanner.java
	javac -classpath ./int/java_cup.jar:./int:./int/ast_classes int/Main.java
	cp src/jar_manifest .
	cp -R src/java_cup .
	cp int/*.class .
	cp int/ast_classes/*.class .
	jar cmf jar_manifest nated.jar *.class java_cup
	mv nated.jar bin/
	rm -f *.class
	rm -rf java_cup
	rm -f jar_manifest

clean:
	rm -fr int
	rm -fr bin
	rm -f *.class
	rm -fr java_cup
	rm -f jar_manifest
