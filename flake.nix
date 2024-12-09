{
  description = "A {nix,flake}-enabled project template for pretty much any type of development";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    systems.url = "github:nix-systems/default/main";
    flake-parts.url = "github:hercules-ci/flake-parts/main";
    flake-parts.inputs.nixpkgs-lib.follows = "nixpkgs";

    # devenv
    devenv = {
      url = "github:cachix/devenv/main";
      inputs.nixpkgs.follows = "nixpkgs";

      # optional
      inputs.flake-compat.follows = "";
      inputs.nix.follows = "";
      inputs.cachix.follows = "";
    };

    devenv-root.url = "file+file:///dev/null";
    devenv-root.flake = false;

    # containers
    nix2container.url = "github:nlewo/nix2container/master";
    nix2container.inputs.nixpkgs.follows = "nixpkgs";

    mk-shell-bin.url = "github:rrbutani/nix-mk-shell-bin/main";

    # formatter
    treefmt-nix.url = "github:numtide/treefmt-nix/main";
    treefmt-nix.inputs.nixpkgs.follows = "nixpkgs";

    # pre-commit
    pre-commit-hooks = {
      url = "github:cachix/git-hooks.nix";
      inputs.nixpkgs.follows = "nixpkgs";
      inputs.nixpkgs-stable.follows = "";
      inputs.flake-compat.follows = "";
      inputs.gitignore.follows = "";
    };
  };

  outputs =
    inputs:
    inputs.flake-parts.lib.mkFlake { inherit inputs; } {
      systems = import inputs.systems;
      imports =
        let
          modulesDir = ./nix/modules;
        in
        with builtins;
        map (mod: "${modulesDir}/${mod}") (attrNames (readDir modulesDir));
    };

  nixConfig = {
    extra-trusted-public-keys = [ "devenv.cachix.org-1:w1cLUi8dv3hnoSPGAuibQv+f9TZLr6cv/Hm9XgU50cw=" ];
    extra-substituters = [ "https://devenv.cachix.org" ];
  };
}
