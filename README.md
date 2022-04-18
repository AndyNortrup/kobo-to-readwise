# kobo-to-readwise

This tiny application takes highlights from a kobo sqlite database and sends them to Readwise.  I'm trying to get back to a smooth flow of highlighting and sending things off to Readwise that I had when I was using a Kindle.

## Usage

1. Get your [Readwise API Key](https://readwise.io/api_deets).
2. Past it into `~/.readwise_api`
3. Make sure you've opened the Kobo app on your mac recently, this will pull down your Kobo database including all of your annotations.
4. Run `(-main)`.  

After this all of your highlights and annotations should be in Readwise. Run it again to pick up new highlights. Currently the script gets and sends all of your highlights everytime you run it because I'm relying on Readwise's API contract to dedup highlights by title, author, and highlight text.

## License

Copyright © 2022 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
