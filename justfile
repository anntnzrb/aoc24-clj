# prints this menu
default:
    @just --list

# format source tree
fmt:
    treefmt

# server notebook
serve:
	clj -X:clerk-serve
