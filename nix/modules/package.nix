_: {
  perSystem =
    { self', pkgs, ... }:
    {
      packages = {
        default = self'.packages.main;

        main = pkgs.writeShellApplication {
          name = "main";
          runtimeInputs = with pkgs; [
            cowsay
            fortune
            lolcat
          ];

          text = ''
            fortune | cowsay | lolcat
          '';
        };
      };
    };
}
