{
  inputs,
  ...
}:
{
  imports = [ inputs.devenv.flakeModule ];
  perSystem =
    {
      config,
      pkgs,
      lib,
      ...
    }:
    {
      devenv.shells.default = {
        name = "annt-devenv-template-shell";

        devenv.root =
          let
            file = builtins.readFile inputs.devenv-root.outPath;
          in
          pkgs.lib.mkIf (file != "") file;

        languages = {
          nix.enable = true;
          clojure.enable = true;
          java.jdk.package = pkgs.temurin-bin-21;
        };

        packages = with pkgs; [
          just
          config.treefmt.build.wrapper
        ];

        enterShell = ''
          cat <<EOF

            🐚✒️ Get started: 'just <recipe>'
            `just`

          EOF
        '';
      };
    };
}
