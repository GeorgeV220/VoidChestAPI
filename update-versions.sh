find ./README.md -type f -exec sed -E -i "s/(<)?(version|small|:|compileOnly|\">)([:' >\"]+)?([0-9]+\.[0-9]+\.[0-9]+(-[a-z0-9]+\.?[0-9]*)?)(<\/version>|\"|['<]?)/\1\2\3$1\6/g" {} \;
