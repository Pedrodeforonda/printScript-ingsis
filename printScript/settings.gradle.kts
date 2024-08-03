
rootProject.name = "untitled"
include("src:main:lexer")
findProject(":src:main:lexer")?.name = "lexer"
include("src:main:lexer")
findProject(":src:main:lexer")?.name = "lexer"
include("src:main:parser")
findProject(":src:main:parser")?.name = "parser"
include("src:main:interpreter")
findProject(":src:main:interpreter")?.name = "interpreter"
include("src:main:ast")
findProject(":src:main:ast")?.name = "ast"
include("src:main:token")
findProject(":src:main:token")?.name = "token"
include("src:main:runner")
findProject(":src:main:runner")?.name = "runner"
