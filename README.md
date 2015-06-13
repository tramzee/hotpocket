# hotpocket

A Leiningen plugin to create a git log file in the resources directory

## Usage

Put `[hotpocket "0.1.0"]` into the `:plugins` vector of your project.clj.

    $ lein hotpocket

This also adds hooks for `lein deploy` and `lein beanstalk deploy` so that a
resource file will be generated when you do either of those deploys

## License

Copyright © 2015 Tim Ramsey

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
