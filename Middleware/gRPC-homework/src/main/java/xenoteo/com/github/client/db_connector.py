import sqlite3


class DBConnector:
    """A class responsible for database connection."""

    def __init__(self):
        self.conn = sqlite3.connect('mo.db')

    def connect(self):
        """A function connecting to the database"""

        self.conn = sqlite3.connect('mo.db')

    def disconnect(self):
        """A function disconnecting from the database"""

        self.conn.close()

    def is_not_present(self, client_id):
        """
        A function checking whether a client with the provided ID is not present in the database.

        :param client_id: the client ID
        :return: whether a client with provided ID is not present in the database
        """

        cursor = self.conn.execute("select * from clients where id = ?", (client_id,))
        return cursor.fetchone() is None

    def add_new_client(self, client_id):
        """
        A function adding a new client to the database based on his ID provided.

        :param client_id: the client ID
        """

        self.conn.execute("insert into clients (id, waiting, issue) values (?, ?, ?)",
                          (client_id, 0, None))
        self.conn.commit()

    def is_waiting(self, client_id):
        """
        A function checking whether client with the provided ID is waiting for the response from the server or not.

        :param client_id: the client ID
        :return: whether client with the provided ID is waiting for the response from the server or not
        """

        cursor = self.conn.execute("select waiting from clients where id = ?", (client_id,))
        waiting = 0
        for row in cursor:
            waiting = row[0]
        return waiting

    def set_waiting(self, client_id, waiting):
        """
        A function setting a client's with the provided ID waiting value.

        :param client_id: the client ID
        :param waiting: the waiting value
        """

        self.conn.execute("update clients set waiting = ? where id = ?", (waiting, client_id))
        self.conn.commit()

    def get_prev_issue(self, client_id):
        """
        A function getting the type of the previous issue requested by a client with the provided ID.

        :param client_id: the client ID
        :return: the type of the previous issue requested
        """

        cursor = self.conn.execute("select issue from clients where id = ?", (client_id,))
        issue = 0
        for row in cursor:
            issue = row[0]
        return issue

    def set_issue(self, client_id, issue):
        """
        A function setting a client's with the provided ID issue value.

        :param client_id: the client ID
        :param issue: the issue value
        """

        self.conn.execute("update clients set issue = ? where id = ?", (issue, client_id))
        self.conn.commit()

    def clients_table_exists(self):
        """
        A function checking whether 'clients' table exists or not.

        :return: whether 'clients' table exists or not
        """

        cursor = self.conn.execute("select name from sqlite_master where type='table' and name='clients'")
        for row in cursor:
            return row[0] is not None
        return False

    def create_clients_table(self):
        """A function creating 'clients' table."""

        self.conn.execute("create table clients (id integer, waiting bit, issue integer)")

