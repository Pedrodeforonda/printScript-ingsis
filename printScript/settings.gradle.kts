rootProject.name = "printScript"
include(":parser")
findProject(":parser")?.name = "parser"
include(":ast")
findProject(":ast")?.name = "ast"
include(":token")
findProject(":token")?.name = "token"
include(":interpreter")
findProject(":interpreter")?.name = "interpreter"
include(":lexer")
findProject(":lexer")?.name = "lexer"
include(":runner")
findProject(":runner")?.name = "runner"
